import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFlowExecution } from 'app/shared/model/flow-execution.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FlowExecutionService } from './flow-execution.service';
import { FlowExecutionDeleteDialogComponent } from './flow-execution-delete-dialog.component';

@Component({
  selector: 'jhi-flow-execution',
  templateUrl: './flow-execution.component.html'
})
export class FlowExecutionComponent implements OnInit, OnDestroy {
  flowExecutions?: IFlowExecution[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected flowExecutionService: FlowExecutionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.flowExecutionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IFlowExecution[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInFlowExecutions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFlowExecution): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFlowExecutions(): void {
    this.eventSubscriber = this.eventManager.subscribe('flowExecutionListModification', () => this.loadPage());
  }

  delete(flowExecution: IFlowExecution): void {
    const modalRef = this.modalService.open(FlowExecutionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.flowExecution = flowExecution;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IFlowExecution[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/flow-execution'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.flowExecutions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
