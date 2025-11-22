import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  imports: [RouterOutlet],
  styleUrls: ['./app.scss']
})
export class App {
  readonly title = 'My Gallery';
}
