import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [RouterModule], 
  templateUrl: './index.html',
  styleUrl: './index.scss'
})
export class IndexPage {

  constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login']);
  }

}
