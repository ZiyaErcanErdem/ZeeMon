import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionScriptDetailComponent } from 'app/entities/action-script/action-script-detail.component';
import { ActionScript } from 'app/shared/model/action-script.model';

describe('Component Tests', () => {
  describe('ActionScript Management Detail Component', () => {
    let comp: ActionScriptDetailComponent;
    let fixture: ComponentFixture<ActionScriptDetailComponent>;
    const route = ({ data: of({ actionScript: new ActionScript(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionScriptDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActionScriptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActionScriptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load actionScript on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.actionScript).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
