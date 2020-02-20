import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EndpointPropertyService } from 'app/entities/endpoint-property/endpoint-property.service';
import { IEndpointProperty, EndpointProperty } from 'app/shared/model/endpoint-property.model';
import { PropKeyType } from 'app/shared/model/enumerations/prop-key-type.model';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

describe('Service Tests', () => {
  describe('EndpointProperty Service', () => {
    let injector: TestBed;
    let service: EndpointPropertyService;
    let httpMock: HttpTestingController;
    let elemDefault: IEndpointProperty;
    let expectedResult: IEndpointProperty | IEndpointProperty[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EndpointPropertyService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EndpointProperty(0, 'AAAAAAA', PropKeyType.ANY, 'AAAAAAA', DataType.STRING, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EndpointProperty', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EndpointProperty()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EndpointProperty', () => {
        const returnedFromService = Object.assign(
          {
            propKey: 'BBBBBB',
            propKeyType: 'BBBBBB',
            propValue: 'BBBBBB',
            propValueType: 'BBBBBB',
            propDescription: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EndpointProperty', () => {
        const returnedFromService = Object.assign(
          {
            propKey: 'BBBBBB',
            propKeyType: 'BBBBBB',
            propValue: 'BBBBBB',
            propValueType: 'BBBBBB',
            propDescription: 'BBBBBB'
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

      it('should delete a EndpointProperty', () => {
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
