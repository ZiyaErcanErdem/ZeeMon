import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { FieldMappingDetailComponent } from 'app/entities/field-mapping/field-mapping-detail.component';
import { FieldMapping } from 'app/shared/model/field-mapping.model';

describe('Component Tests', () => {
  describe('FieldMapping Management Detail Component', () => {
    let comp: FieldMappingDetailComponent;
    let fixture: ComponentFixture<FieldMappingDetailComponent>;
    const route = ({ data: of({ fieldMapping: new FieldMapping(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [FieldMappingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FieldMappingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FieldMappingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fieldMapping on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fieldMapping).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
