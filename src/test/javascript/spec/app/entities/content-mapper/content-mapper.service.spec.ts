import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ContentMapperService } from 'app/entities/content-mapper/content-mapper.service';
import { IContentMapper, ContentMapper } from 'app/shared/model/content-mapper.model';
import { ItemFormat } from 'app/shared/model/enumerations/item-format.model';

describe('Service Tests', () => {
  describe('ContentMapper Service', () => {
    let injector: TestBed;
    let service: ContentMapperService;
    let httpMock: HttpTestingController;
    let elemDefault: IContentMapper;
    let expectedResult: IContentMapper | IContentMapper[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContentMapperService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ContentMapper(0, 'AAAAAAA', ItemFormat.SQL_RESULTSET, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ContentMapper', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ContentMapper()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ContentMapper', () => {
        const returnedFromService = Object.assign(
          {
            mapperName: 'BBBBBB',
            itemFormat: 'BBBBBB',
            containsHeader: true,
            fieldDelimiter: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ContentMapper', () => {
        const returnedFromService = Object.assign(
          {
            mapperName: 'BBBBBB',
            itemFormat: 'BBBBBB',
            containsHeader: true,
            fieldDelimiter: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ContentMapper', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
