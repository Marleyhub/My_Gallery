import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root' // makes it globally available
})
export class AuthService {
  private tokenKey = 'token';
  private userKey = 'user';

  setAuthData(user: any, token: string): void {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUser(): any | null {
    const data = localStorage.getItem(this.userKey);
    return data ? JSON.parse(data) : null;
  }

  clearAuthData(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }
}
