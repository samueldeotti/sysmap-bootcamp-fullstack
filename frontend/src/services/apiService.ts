import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:3001',
});

export const albumApi = axios.create({
  baseURL: 'http://localhost:8082/api',
});

export const userApi = axios.create({
  baseURL: 'http://localhost:8081/api',
});
