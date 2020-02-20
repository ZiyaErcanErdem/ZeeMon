import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { FlowExecutionDetailComponent } from 'app/entities/flow-execution/flow-execution-detail.component';
import { FlowExecution } from 'app/shared/model/flow-execution.model';

describe('Component Tests', () => {
  describe('FlowExecution Management Detail Component', () => {
    let comp: FlowExecutionDetailComponent;
    let fixture: ComponentFixture<FlowExecutionDetailComponent>;
    const route = ({ data: of({ flowExecution: new FlowExecution(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [FlowExecutionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FlowExecutionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FlowExecutionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load flowExecution on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.flowExecution).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
