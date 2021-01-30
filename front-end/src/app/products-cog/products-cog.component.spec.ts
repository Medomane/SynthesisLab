import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsCogComponent } from './products-cog.component';

describe('ProductsCogComponent', () => {
  let component: ProductsCogComponent;
  let fixture: ComponentFixture<ProductsCogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductsCogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductsCogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
