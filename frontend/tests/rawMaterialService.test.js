import { describe, it, expect, vi, beforeEach } from 'vitest'
import axios from 'axios'
import rawMaterialService from '../src/services/rawMaterialService'

vi.mock('axios')

describe('rawMaterialService', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('calls getAll with the correct base URL', () => {
    rawMaterialService.getAll()
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/raw-materials')
  })

  it('calls getById with the correct detail URL', () => {
    rawMaterialService.getById(10)
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/raw-materials/10')
  })

  it('calls create with the base URL and payload', () => {
    const payload = { name: 'Raw material' }
    rawMaterialService.create(payload)
    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/raw-materials', payload)
  })

  it('calls update with the detail URL and payload', () => {
    const payload = { name: 'Raw material updated' }
    rawMaterialService.update(5, payload)
    expect(axios.put).toHaveBeenCalledWith('http://localhost:8080/api/raw-materials/5', payload)
  })

  it('calls delete with the correct detail URL', () => {
    rawMaterialService.delete(7)
    expect(axios.delete).toHaveBeenCalledWith('http://localhost:8080/api/raw-materials/7')
  })
})
