import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EventTriggerService } from 'app/entities/event-trigger/event-trigger.service';
import { IEventTrigger, EventTrigger } from 'app/shared/model/event-trigger.model';
import { TriggerType } from 'app/shared/model/enumerations/trigger-type.model';
import { TimeUnit } from 'app/shared/model/enumerations/time-unit.model';

describe('Service Tests', () => {
  describe('EventTrigger Service', () => {
    let injector: TestBed;
    let service: EventTriggerService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventTrigger;
    let expectedResult: IEventTrigger | IEventTrigger[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventTriggerService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EventTrigger(0, 'AAAAAAA', TriggerType.MANUAL, 0, TimeUnit.SECOND, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EventTrigger', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EventTrigger()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EventTrigger', () => {
        const returnedFromService = Object.assign(
          {
            triggerName: 'BBBBBB',
            triggerType: 'BBBBBB',
            triggerPeriod: 1,
            triggerTimeUnit: 'BBBBBB',
            triggerCronExpression: 'BBBBBB',
            triggerCronTimeZone: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EventTrigger', () => {
        const returnedFromService = Object.assign(
          {
            triggerName: 'BBBBBB',
            triggerType: 'BBBBBB',
            triggerPeriod: 1,
            triggerTimeUnit: 'BBBBBB',
            triggerCronExpression: 'BBBBBB',
            triggerCronTimeZone: 'BBBBBB'
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

      it('should delete a EventTrigger', () => {
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
