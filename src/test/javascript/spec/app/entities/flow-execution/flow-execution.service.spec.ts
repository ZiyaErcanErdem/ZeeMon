import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FlowExecutionService } from 'app/entities/flow-execution/flow-execution.service';
import { IFlowExecution, FlowExecution } from 'app/shared/model/flow-execution.model';
import { ExecutionStatus } from 'app/shared/model/enumerations/execution-status.model';

describe('Service Tests', () => {
  describe('FlowExecution Service', () => {
    let injector: TestBed;
    let service: FlowExecutionService;
    let httpMock: HttpTestingController;
    let elemDefault: IFlowExecution;
    let expectedResult: IFlowExecution | IFlowExecution[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FlowExecutionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FlowExecution(0, currentDate, currentDate, 0, 0, 0, ExecutionStatus.PENDING);
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

      it('should create a FlowExecution', () => {
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

        service.create(new FlowExecution()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FlowExecution', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT),
            totalTaskCount: 1,
            runningTaskCount: 1,
            errorTaskCount: 1,
            executionStatus: 'BBBBBB'
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

      it('should return a list of FlowExecution', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT),
            totalTaskCount: 1,
            runningTaskCount: 1,
            errorTaskCount: 1,
            executionStatus: 'BBBBBB'
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

      it('should delete a FlowExecution', () => {
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
