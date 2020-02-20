import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ActionExecutionService } from 'app/entities/action-execution/action-execution.service';
import { IActionExecution, ActionExecution } from 'app/shared/model/action-execution.model';
import { ExecutionStatus } from 'app/shared/model/enumerations/execution-status.model';

describe('Service Tests', () => {
  describe('ActionExecution Service', () => {
    let injector: TestBed;
    let service: ActionExecutionService;
    let httpMock: HttpTestingController;
    let elemDefault: IActionExecution;
    let expectedResult: IActionExecution | IActionExecution[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ActionExecutionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ActionExecution(0, currentDate, currentDate, ExecutionStatus.PENDING, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ActionExecution', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executionStartTime: currentDate,
            executionEndTime: currentDate
          },
          returnedFromService
        );

        service.create(new ActionExecution()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ActionExecution', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT),
            executionStatus: 'BBBBBB',
            actionLog: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executionStartTime: currentDate,
            executionEndTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ActionExecution', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT),
            executionStatus: 'BBBBBB',
            actionLog: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executionStartTime: currentDate,
            executionEndTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ActionExecution', () => {
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
