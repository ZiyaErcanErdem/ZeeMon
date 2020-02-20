import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ContentService } from 'app/entities/content/content.service';
import { IContent, Content } from 'app/shared/model/content.model';

describe('Service Tests', () => {
  describe('Content Service', () => {
    let injector: TestBed;
    let service: ContentService;
    let httpMock: HttpTestingController;
    let elemDefault: IContent;
    let expectedResult: IContent | IContent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Content(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date1: currentDate.format(DATE_TIME_FORMAT),
            date2: currentDate.format(DATE_TIME_FORMAT),
            date3: currentDate.format(DATE_TIME_FORMAT),
            date4: currentDate.format(DATE_TIME_FORMAT),
            date5: currentDate.format(DATE_TIME_FORMAT),
            date6: currentDate.format(DATE_TIME_FORMAT),
            date7: currentDate.format(DATE_TIME_FORMAT),
            date8: currentDate.format(DATE_TIME_FORMAT),
            date9: currentDate.format(DATE_TIME_FORMAT),
            date10: currentDate.format(DATE_TIME_FORMAT),
            bool1: currentDate.format(DATE_TIME_FORMAT),
            bool2: currentDate.format(DATE_TIME_FORMAT),
            bool3: currentDate.format(DATE_TIME_FORMAT),
            bool4: currentDate.format(DATE_TIME_FORMAT),
            bool5: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Content', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date1: currentDate.format(DATE_TIME_FORMAT),
            date2: currentDate.format(DATE_TIME_FORMAT),
            date3: currentDate.format(DATE_TIME_FORMAT),
            date4: currentDate.format(DATE_TIME_FORMAT),
            date5: currentDate.format(DATE_TIME_FORMAT),
            date6: currentDate.format(DATE_TIME_FORMAT),
            date7: currentDate.format(DATE_TIME_FORMAT),
            date8: currentDate.format(DATE_TIME_FORMAT),
            date9: currentDate.format(DATE_TIME_FORMAT),
            date10: currentDate.format(DATE_TIME_FORMAT),
            bool1: currentDate.format(DATE_TIME_FORMAT),
            bool2: currentDate.format(DATE_TIME_FORMAT),
            bool3: currentDate.format(DATE_TIME_FORMAT),
            bool4: currentDate.format(DATE_TIME_FORMAT),
            bool5: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date1: currentDate,
            date2: currentDate,
            date3: currentDate,
            date4: currentDate,
            date5: currentDate,
            date6: currentDate,
            date7: currentDate,
            date8: currentDate,
            date9: currentDate,
            date10: currentDate,
            bool1: currentDate,
            bool2: currentDate,
            bool3: currentDate,
            bool4: currentDate,
            bool5: currentDate
          },
          returnedFromService
        );

        service.create(new Content()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Content', () => {
        const returnedFromService = Object.assign(
          {
            sourceIndex: 1,
            txt1: 'BBBBBB',
            txt2: 'BBBBBB',
            txt3: 'BBBBBB',
            txt4: 'BBBBBB',
            txt5: 'BBBBBB',
            txt6: 'BBBBBB',
            txt7: 'BBBBBB',
            txt8: 'BBBBBB',
            txt9: 'BBBBBB',
            txt10: 'BBBBBB',
            txt11: 'BBBBBB',
            txt12: 'BBBBBB',
            txt13: 'BBBBBB',
            txt14: 'BBBBBB',
            txt15: 'BBBBBB',
            txt16: 'BBBBBB',
            txt17: 'BBBBBB',
            txt18: 'BBBBBB',
            txt19: 'BBBBBB',
            txt20: 'BBBBBB',
            num1: 1,
            num2: 1,
            num3: 1,
            num4: 1,
            num5: 1,
            num6: 1,
            num7: 1,
            num8: 1,
            num9: 1,
            num10: 1,
            num11: 1,
            num12: 1,
            num13: 1,
            num14: 1,
            num15: 1,
            num16: 1,
            num17: 1,
            num18: 1,
            num19: 1,
            num20: 1,
            date1: currentDate.format(DATE_TIME_FORMAT),
            date2: currentDate.format(DATE_TIME_FORMAT),
            date3: currentDate.format(DATE_TIME_FORMAT),
            date4: currentDate.format(DATE_TIME_FORMAT),
            date5: currentDate.format(DATE_TIME_FORMAT),
            date6: currentDate.format(DATE_TIME_FORMAT),
            date7: currentDate.format(DATE_TIME_FORMAT),
            date8: currentDate.format(DATE_TIME_FORMAT),
            date9: currentDate.format(DATE_TIME_FORMAT),
            date10: currentDate.format(DATE_TIME_FORMAT),
            bool1: currentDate.format(DATE_TIME_FORMAT),
            bool2: currentDate.format(DATE_TIME_FORMAT),
            bool3: currentDate.format(DATE_TIME_FORMAT),
            bool4: currentDate.format(DATE_TIME_FORMAT),
            bool5: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date1: currentDate,
            date2: currentDate,
            date3: currentDate,
            date4: currentDate,
            date5: currentDate,
            date6: currentDate,
            date7: currentDate,
            date8: currentDate,
            date9: currentDate,
            date10: currentDate,
            bool1: currentDate,
            bool2: currentDate,
            bool3: currentDate,
            bool4: currentDate,
            bool5: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Content', () => {
        const returnedFromService = Object.assign(
          {
            sourceIndex: 1,
            txt1: 'BBBBBB',
            txt2: 'BBBBBB',
            txt3: 'BBBBBB',
            txt4: 'BBBBBB',
            txt5: 'BBBBBB',
            txt6: 'BBBBBB',
            txt7: 'BBBBBB',
            txt8: 'BBBBBB',
            txt9: 'BBBBBB',
            txt10: 'BBBBBB',
            txt11: 'BBBBBB',
            txt12: 'BBBBBB',
            txt13: 'BBBBBB',
            txt14: 'BBBBBB',
            txt15: 'BBBBBB',
            txt16: 'BBBBBB',
            txt17: 'BBBBBB',
            txt18: 'BBBBBB',
            txt19: 'BBBBBB',
            txt20: 'BBBBBB',
            num1: 1,
            num2: 1,
            num3: 1,
            num4: 1,
            num5: 1,
            num6: 1,
            num7: 1,
            num8: 1,
            num9: 1,
            num10: 1,
            num11: 1,
            num12: 1,
            num13: 1,
            num14: 1,
            num15: 1,
            num16: 1,
            num17: 1,
            num18: 1,
            num19: 1,
            num20: 1,
            date1: currentDate.format(DATE_TIME_FORMAT),
            date2: currentDate.format(DATE_TIME_FORMAT),
            date3: currentDate.format(DATE_TIME_FORMAT),
            date4: currentDate.format(DATE_TIME_FORMAT),
            date5: currentDate.format(DATE_TIME_FORMAT),
            date6: currentDate.format(DATE_TIME_FORMAT),
            date7: currentDate.format(DATE_TIME_FORMAT),
            date8: currentDate.format(DATE_TIME_FORMAT),
            date9: currentDate.format(DATE_TIME_FORMAT),
            date10: currentDate.format(DATE_TIME_FORMAT),
            bool1: currentDate.format(DATE_TIME_FORMAT),
            bool2: currentDate.format(DATE_TIME_FORMAT),
            bool3: currentDate.format(DATE_TIME_FORMAT),
            bool4: currentDate.format(DATE_TIME_FORMAT),
            bool5: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date1: currentDate,
            date2: currentDate,
            date3: currentDate,
            date4: currentDate,
            date5: currentDate,
            date6: currentDate,
            date7: currentDate,
            date8: currentDate,
            date9: currentDate,
            date10: currentDate,
            bool1: currentDate,
            bool2: currentDate,
            bool3: currentDate,
            bool4: currentDate,
            bool5: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Content', () => {
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
