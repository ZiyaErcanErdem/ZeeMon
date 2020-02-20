import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AgentService } from 'app/entities/agent/agent.service';
import { IAgent, Agent } from 'app/shared/model/agent.model';
import { AgentType } from 'app/shared/model/enumerations/agent-type.model';
import { AgentStatus } from 'app/shared/model/enumerations/agent-status.model';

describe('Service Tests', () => {
  describe('Agent Service', () => {
    let injector: TestBed;
    let service: AgentService;
    let httpMock: HttpTestingController;
    let elemDefault: IAgent;
    let expectedResult: IAgent | IAgent[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AgentService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Agent(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', AgentType.SQL_AGENT, AgentStatus.ACTIVE, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Agent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Agent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Agent', () => {
        const returnedFromService = Object.assign(
          {
            agentName: 'BBBBBB',
            agentInstanceId: 'BBBBBB',
            listenURI: 'BBBBBB',
            agentType: 'BBBBBB',
            agentStatus: 'BBBBBB',
            agentDescription: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Agent', () => {
        const returnedFromService = Object.assign(
          {
            agentName: 'BBBBBB',
            agentInstanceId: 'BBBBBB',
            listenURI: 'BBBBBB',
            agentType: 'BBBBBB',
            agentStatus: 'BBBBBB',
            agentDescription: 'BBBBBB'
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

      it('should delete a Agent', () => {
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
