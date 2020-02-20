import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ContentValidationErrorDetailComponent } from 'app/entities/content-validation-error/content-validation-error-detail.component';
import { ContentValidationError } from 'app/shared/model/content-validation-error.model';

describe('Component Tests', () => {
  describe('ContentValidationError Management Detail Component', () => {
    let comp: ContentValidationErrorDetailComponent;
    let fixture: ComponentFixture<ContentValidationErrorDetailComponent>;
    const route = ({ data: of({ contentValidationError: new ContentValidationError(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ContentValidationErrorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContentValidationErrorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentValidationErrorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contentValidationError on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentValidationError).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
