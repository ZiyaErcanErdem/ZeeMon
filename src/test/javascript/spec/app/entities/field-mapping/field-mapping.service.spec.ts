import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FieldMappingService } from 'app/entities/field-mapping/field-mapping.service';
import { IFieldMapping, FieldMapping } from 'app/shared/model/field-mapping.model';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

describe('Service Tests', () => {
  describe('FieldMapping Service', () => {
    let injector: TestBed;
    let service: FieldMappingService;
    let httpMock: HttpTestingController;
    let elemDefault: IFieldMapping;
    let expectedResult: IFieldMapping | IFieldMapping[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FieldMappingService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FieldMapping(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        DataType.STRING,
        'AAAAAAA',
        'AAAAAAA',
        DataType.STRING,
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FieldMapping', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FieldMapping()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FieldMapping', () => {
        const returnedFromService = Object.assign(
          {
            sourceIndex: 1,
            sourceName: 'BBBBBB',
            sourceFormat: 'BBBBBB',
            sourceStartIndex: 1,
            sourceEndIndex: 1,
            sourceDataType: 'BBBBBB',
            targetName: 'BBBBBB',
            targetColName: 'BBBBBB',
            targetDataType: 'BBBBBB',
            transformation: 'BBBBBB',
            requiredData: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FieldMapping', () => {
        const returnedFromService = Object.assign(
          {
            sourceIndex: 1,
            sourceName: 'BBBBBB',
            sourceFormat: 'BBBBBB',
            sourceStartIndex: 1,
            sourceEndIndex: 1,
            sourceDataType: 'BBBBBB',
            targetName: 'BBBBBB',
            targetColName: 'BBBBBB',
            targetDataType: 'BBBBBB',
            transformation: 'BBBBBB',
            requiredData: true
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

      it('should delete a FieldMapping', () => {
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
