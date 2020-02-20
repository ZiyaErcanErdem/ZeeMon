import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ActionService } from 'app/entities/action/action.service';
import { IAction, Action } from 'app/shared/model/action.model';
import { ActionState } from 'app/shared/model/enumerations/action-state.model';

describe('Service Tests', () => {
  describe('Action Service', () => {
    let injector: TestBed;
    let service: ActionService;
    let httpMock: HttpTestingController;
    let elemDefault: IAction;
    let expectedResult: IAction | IAction[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ActionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Action(0, 'AAAAAAA', 'AAAAAAA', currentDate, ActionState.PENDING, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            nextExecutionStartTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Action', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            nextExecutionStartTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nextExecutionStartTime: currentDate
          },
          returnedFromService
        );

        service.create(new Action()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Action', () => {
        const returnedFromService = Object.assign(
          {
            actionName: 'BBBBBB',
            actionDescription: 'BBBBBB',
            nextExecutionStartTime: currentDate.format(DATE_TIME_FORMAT),
            actionState: 'BBBBBB',
            resolutionRuleExpression: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nextExecutionStartTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Action', () => {
        const returnedFromService = Object.assign(
          {
            actionName: 'BBBBBB',
            actionDescription: 'BBBBBB',
            nextExecutionStartTime: currentDate.format(DATE_TIME_FORMAT),
            actionState: 'BBBBBB',
            resolutionRuleExpression: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nextExecutionStartTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Action', () => {
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
