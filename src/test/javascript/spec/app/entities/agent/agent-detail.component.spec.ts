import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { AgentDetailComponent } from 'app/entities/agent/agent-detail.component';
import { Agent } from 'app/shared/model/agent.model';

describe('Component Tests', () => {
  describe('Agent Management Detail Component', () => {
    let comp: AgentDetailComponent;
    let fixture: ComponentFixture<AgentDetailComponent>;
    const route = ({ data: of({ agent: new Agent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [AgentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load agent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
