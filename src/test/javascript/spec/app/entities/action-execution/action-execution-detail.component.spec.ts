import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionExecutionDetailComponent } from 'app/entities/action-execution/action-execution-detail.component';
import { ActionExecution } from 'app/shared/model/action-execution.model';

describe('Component Tests', () => {
  describe('ActionExecution Management Detail Component', () => {
    let comp: ActionExecutionDetailComponent;
    let fixture: ComponentFixture<ActionExecutionDetailComponent>;
    const route = ({ data: of({ actionExecution: new ActionExecution(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionExecutionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActionExecutionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActionExecutionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load actionExecution on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.actionExecution).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
