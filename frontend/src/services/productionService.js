import axios from 'axios';
const API_URL = 'http://localhost:8080/api/production';

const productionService = {
  optimize() { return axios.get(`${API_URL}/optimize`); }
};

export default productionService;