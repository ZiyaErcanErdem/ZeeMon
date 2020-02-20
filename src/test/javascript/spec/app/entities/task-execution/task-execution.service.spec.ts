import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TaskExecutionService } from 'app/entities/task-execution/task-execution.service';
import { ITaskExecution, TaskExecution } from 'app/shared/model/task-execution.model';
import { ExecutionStatus } from 'app/shared/model/enumerations/execution-status.model';

describe('Service Tests', () => {
  describe('TaskExecution Service', () => {
    let injector: TestBed;
    let service: TaskExecutionService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaskExecution;
    let expectedResult: ITaskExecution | ITaskExecution[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TaskExecutionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TaskExecution(0, currentDate, currentDate, ExecutionStatus.PENDING);
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

      it('should create a TaskExecution', () => {
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

        service.create(new TaskExecution()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaskExecution', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should return a list of TaskExecution', () => {
        const returnedFromService = Object.assign(
          {
            executionStartTime: currentDate.format(DATE_TIME_FORMAT),
            executionEndTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should delete a TaskExecution', () => {
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
