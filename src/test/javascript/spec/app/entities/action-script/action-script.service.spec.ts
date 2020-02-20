import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActionScriptService } from 'app/entities/action-script/action-script.service';
import { IActionScript, ActionScript } from 'app/shared/model/action-script.model';
import { ScriptType } from 'app/shared/model/enumerations/script-type.model';
import { ActionType } from 'app/shared/model/enumerations/action-type.model';

describe('Service Tests', () => {
  describe('ActionScript Service', () => {
    let injector: TestBed;
    let service: ActionScriptService;
    let httpMock: HttpTestingController;
    let elemDefault: IActionScript;
    let expectedResult: IActionScript | IActionScript[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ActionScriptService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ActionScript(0, 'AAAAAAA', 'AAAAAAA', ScriptType.SQL_SCRIPT, ActionType.EMAIL, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ActionScript', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ActionScript()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ActionScript', () => {
        const returnedFromService = Object.assign(
          {
            actionCode: 'BBBBBB',
            scriptName: 'BBBBBB',
            scriptType: 'BBBBBB',
            actionType: 'BBBBBB',
            actionSource: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ActionScript', () => {
        const returnedFromService = Object.assign(
          {
            actionCode: 'BBBBBB',
            scriptName: 'BBBBBB',
            scriptType: 'BBBBBB',
            actionType: 'BBBBBB',
            actionSource: 'BBBBBB'
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

      it('should delete a ActionScript', () => {
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
