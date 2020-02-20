import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { CheckScriptDetailComponent } from 'app/entities/check-script/check-script-detail.component';
import { CheckScript } from 'app/shared/model/check-script.model';

describe('Component Tests', () => {
  describe('CheckScript Management Detail Component', () => {
    let comp: CheckScriptDetailComponent;
    let fixture: ComponentFixture<CheckScriptDetailComponent>;
    const route = ({ data: of({ checkScript: new CheckScript(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [CheckScriptDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CheckScriptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckScriptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkScript on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkScript).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
