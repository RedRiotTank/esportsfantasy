import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellplayerModalComponent } from './sellplayer-modal.component';

describe('SellplayerModalComponent', () => {
  let component: SellplayerModalComponent;
  let fixture: ComponentFixture<SellplayerModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SellplayerModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SellplayerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
