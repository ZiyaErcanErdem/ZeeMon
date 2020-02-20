import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContentValidationError } from 'app/shared/model/content-validation-error.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContentValidationErrorService } from './content-validation-error.service';
import { ContentValidationErrorDeleteDialogComponent } from './content-validation-error-delete-dialog.component';

@Component({
  selector: 'jhi-content-validation-error',
  templateUrl: './content-validation-error.component.html'
})
export class ContentValidationErrorComponent implements OnInit, OnDestroy {
  contentValidationErrors?: IContentValidationError[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected contentValidationErrorService: ContentValidationErrorService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.contentValidationErrorService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IContentValidationError[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInContentValidationErrors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContentValidationError): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContentValidationErrors(): void {
    this.eventSubscriber = this.eventManager.subscribe('contentValidationErrorListModification', () => this.loadPage());
  }

  delete(contentValidationError: IContentValidationError): void {
    const modalRef = this.modalService.open(ContentValidationErrorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contentValidationError = contentValidationError;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IContentValidationError[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/content-validation-error'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.contentValidationErrors = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
