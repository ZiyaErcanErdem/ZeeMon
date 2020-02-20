import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ContentMapperDetailComponent } from 'app/entities/content-mapper/content-mapper-detail.component';
import { ContentMapper } from 'app/shared/model/content-mapper.model';

describe('Component Tests', () => {
  describe('ContentMapper Management Detail Component', () => {
    let comp: ContentMapperDetailComponent;
    let fixture: ComponentFixture<ContentMapperDetailComponent>;
    const route = ({ data: of({ contentMapper: new ContentMapper(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ContentMapperDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContentMapperDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentMapperDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contentMapper on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentMapper).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
