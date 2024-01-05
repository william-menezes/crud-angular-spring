import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, first, tap } from 'rxjs';
import { Course } from '../model/course';

@Injectable({
  providedIn: 'root',
})
export class CoursesService {
  constructor(private http: HttpClient) {}

  private readonly API = 'api/courses';

  list(): Observable<Course[]> {
    return this.http.get<Course[]>(this.API).pipe(
      first(),
      //delay(2000),
      tap((courses) => console.log(courses))
    );
  }

  loadById(id: string): Observable<Course> {
    return this.http.get<Course>(`${this.API}/${id}`);
  }

  save(course: Partial<Course>): Observable<Course> {
    if (course._id) {
      return this.update(course);
    }
    return this.create(course);
  }

  remove(id: string) {
    return this.http.delete(`${this.API}/${id}`).pipe(first());
  }

  private create(course: Partial<Course>): Observable<Course> {
    return this.http.post<Course>(this.API, course).pipe(first());
  }

  private update(course: Partial<Course>): Observable<Course> {
    return this.http
      .put<Course>(`${this.API}/${course._id}`, course)
      .pipe(first());
  }
}
