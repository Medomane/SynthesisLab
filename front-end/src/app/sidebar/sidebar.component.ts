import { Component} from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import {SidebarService} from './sidebar.service';
import {ItemModule} from './item/item.module';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  animations: [
    trigger('slide', [
      state('up', style({ height: 0 })),
      state('down', style({ height: '*' })),
      transition('up <=> down', animate(200))
    ])
  ]
})
export class SidebarComponent{
  menus : ItemModule[] = [];
  constructor(public sidebarService: SidebarService) {
    this.menus = sidebarService.getMenuList();
  }

  getSideBarState() {
    return this.sidebarService.getSidebarState();
  }

  toggle(currentMenu:any) {
    if (currentMenu.type === 'dropdown') {
      this.menus.forEach(element => {
        if (element === currentMenu) {
          currentMenu.active = !currentMenu.active;
        } else {
          // @ts-ignore
          element.active = false;
        }
      });
    }
  }

  getState(currentMenu:any) {

    if (currentMenu.active) {
      return 'down';
    } else {
      return 'up';
    }
  }

}
