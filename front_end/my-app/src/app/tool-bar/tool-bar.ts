import { Component } from '@angular/core';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-tool-bar',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule ],
  templateUrl: './tool-bar.html',
  styleUrl: './tool-bar.scss'
})
export class ToolBar {

}
