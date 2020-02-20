import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEndpointProperty } from 'app/shared/model/endpoint-property.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EndpointPropertyService } from './endpoint-property.service';
import { EndpointPropertyDeleteDialogComponent } from './endpoint-property-delete-dialog.component';

@Component({
  selector: 'jhi-endpoint-property',
  templateUrl: './endpoint-property.component.html'
})
export class EndpointPropertyComponent implements OnInit, OnDestroy {
  endpointProperties?: IEndpointProperty[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected endpointPropertyService: EndpointPropertyService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.endpointPropertyService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEndpointProperty[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEndpointProperties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEndpointProperty): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEndpointProperties(): void {
    this.eventSubscriber = this.eventManager.subscribe('endpointPropertyListModification', () => this.loadPage());
  }

  delete(endpointProperty: IEndpointProperty): void {
    const modalRef = this.modalService.open(EndpointPropertyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.endpointProperty = endpointProperty;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEndpointProperty[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/endpoint-property'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.endpointProperties = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
