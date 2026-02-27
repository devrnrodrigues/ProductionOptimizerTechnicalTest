import { describe, it, expect, vi, beforeEach } from 'vitest'
import axios from 'axios'
import productionService from '../src/services/productionService'

vi.mock('axios')

describe('productionService', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('calls optimize with the correct URL', () => {
    productionService.optimize()
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/production/optimize')
  })
})
