import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContentMapper } from 'app/shared/model/content-mapper.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContentMapperService } from './content-mapper.service';
import { ContentMapperDeleteDialogComponent } from './content-mapper-delete-dialog.component';

@Component({
  selector: 'jhi-content-mapper',
  templateUrl: './content-mapper.component.html'
})
export class ContentMapperComponent implements OnInit, OnDestroy {
  contentMappers?: IContentMapper[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected contentMapperService: ContentMapperService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.contentMapperService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IContentMapper[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInContentMappers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContentMapper): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContentMappers(): void {
    this.eventSubscriber = this.eventManager.subscribe('contentMapperListModification', () => this.loadPage());
  }

  delete(contentMapper: IContentMapper): void {
    const modalRef = this.modalService.open(ContentMapperDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contentMapper = contentMapper;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IContentMapper[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/content-mapper'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.contentMappers = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
