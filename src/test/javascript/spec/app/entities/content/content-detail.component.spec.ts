import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ContentDetailComponent } from 'app/entities/content/content-detail.component';
import { Content } from 'app/shared/model/content.model';

describe('Component Tests', () => {
  describe('Content Management Detail Component', () => {
    let comp: ContentDetailComponent;
    let fixture: ComponentFixture<ContentDetailComponent>;
    const route = ({ data: of({ content: new Content(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ContentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load content on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.content).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
