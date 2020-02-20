import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ScriptParamDetailComponent } from 'app/entities/script-param/script-param-detail.component';
import { ScriptParam } from 'app/shared/model/script-param.model';

describe('Component Tests', () => {
  describe('ScriptParam Management Detail Component', () => {
    let comp: ScriptParamDetailComponent;
    let fixture: ComponentFixture<ScriptParamDetailComponent>;
    const route = ({ data: of({ scriptParam: new ScriptParam(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ScriptParamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScriptParamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScriptParamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scriptParam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scriptParam).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
