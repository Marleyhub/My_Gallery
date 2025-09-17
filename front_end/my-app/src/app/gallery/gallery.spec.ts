import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleryGrid } from './gallery';

describe('Gallery', () => {
  let component: GalleryGrid;
  let fixture: ComponentFixture<GalleryGrid>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GalleryGrid]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GalleryGrid);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
