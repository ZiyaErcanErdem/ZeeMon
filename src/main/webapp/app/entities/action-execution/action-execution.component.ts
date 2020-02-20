import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActionExecution } from 'app/shared/model/action-execution.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActionExecutionService } from './action-execution.service';
import { ActionExecutionDeleteDialogComponent } from './action-execution-delete-dialog.component';

@Component({
  selector: 'jhi-action-execution',
  templateUrl: './action-execution.component.html'
})
export class ActionExecutionComponent implements OnInit, OnDestroy {
  actionExecutions?: IActionExecution[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected actionExecutionService: ActionExecutionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.actionExecutionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IActionExecution[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInActionExecutions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IActionExecution): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActionExecutions(): void {
    this.eventSubscriber = this.eventManager.subscribe('actionExecutionListModification', () => this.loadPage());
  }

  delete(actionExecution: IActionExecution): void {
    const modalRef = this.modalService.open(ActionExecutionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.actionExecution = actionExecution;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IActionExecution[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/action-execution'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.actionExecutions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
