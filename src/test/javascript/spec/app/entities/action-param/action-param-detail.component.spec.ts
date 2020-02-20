import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionParamDetailComponent } from 'app/entities/action-param/action-param-detail.component';
import { ActionParam } from 'app/shared/model/action-param.model';

describe('Component Tests', () => {
  describe('ActionParam Management Detail Component', () => {
    let comp: ActionParamDetailComponent;
    let fixture: ComponentFixture<ActionParamDetailComponent>;
    const route = ({ data: of({ actionParam: new ActionParam(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionParamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActionParamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActionParamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load actionParam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.actionParam).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
