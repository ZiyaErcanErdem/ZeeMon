import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { EndpointPropertyDetailComponent } from 'app/entities/endpoint-property/endpoint-property-detail.component';
import { EndpointProperty } from 'app/shared/model/endpoint-property.model';

describe('Component Tests', () => {
  describe('EndpointProperty Management Detail Component', () => {
    let comp: EndpointPropertyDetailComponent;
    let fixture: ComponentFixture<EndpointPropertyDetailComponent>;
    const route = ({ data: of({ endpointProperty: new EndpointProperty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [EndpointPropertyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EndpointPropertyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EndpointPropertyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load endpointProperty on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.endpointProperty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
