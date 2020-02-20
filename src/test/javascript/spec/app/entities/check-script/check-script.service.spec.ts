import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CheckScriptService } from 'app/entities/check-script/check-script.service';
import { ICheckScript, CheckScript } from 'app/shared/model/check-script.model';
import { ScriptType } from 'app/shared/model/enumerations/script-type.model';

describe('Service Tests', () => {
  describe('CheckScript Service', () => {
    let injector: TestBed;
    let service: CheckScriptService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckScript;
    let expectedResult: ICheckScript | ICheckScript[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CheckScriptService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CheckScript(0, 'AAAAAAA', ScriptType.SQL_SCRIPT, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CheckScript', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CheckScript()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckScript', () => {
        const returnedFromService = Object.assign(
          {
            scriptName: 'BBBBBB',
            scriptType: 'BBBBBB',
            scriptSource: 'BBBBBB',
            actionRuleExpression: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CheckScript', () => {
        const returnedFromService = Object.assign(
          {
            scriptName: 'BBBBBB',
            scriptType: 'BBBBBB',
            scriptSource: 'BBBBBB',
            actionRuleExpression: 'BBBBBB'
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

      it('should delete a CheckScript', () => {
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
