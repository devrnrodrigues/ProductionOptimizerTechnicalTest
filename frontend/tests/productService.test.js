import { describe, it, expect, vi, beforeEach } from 'vitest'
import axios from 'axios'
import productService from '../src/services/productService'

vi.mock('axios')

describe('productService', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('calls getAll with the correct base URL', () => {
    productService.getAll()
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/products')
  })

  it('calls getById with the correct detail URL', () => {
    productService.getById(1)
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/products/1')
  })

  it('calls create with the base URL and payload', () => {
    const payload = { name: 'Product X' }
    productService.create(payload)
    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/products', payload)
  })

  it('calls update with the detail URL and payload', () => {
    const payload = { name: 'Product Y' }
    productService.update(2, payload)
    expect(axios.put).toHaveBeenCalledWith('http://localhost:8080/api/products/2', payload)
  })

  it('calls delete with the correct detail URL', () => {
    productService.delete(3)
    expect(axios.delete).toHaveBeenCalledWith('http://localhost:8080/api/products/3')
  })
})
