import React, { useEffect, useState } from 'react'
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete'
import AlertDialog from '../components/AlertDialog';
import { Alert, Box, Button, Input, TextField } from '@mui/material';
import { toast } from 'react-hot-toast';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import { productEndpoints, userEndpoints } from '../services/apis';
import axios from 'axios';

const categoryMappings = [{
  cat_id: 1,
  name: "Teracotta",
},
{
  cat_id: 2,
  name: "Moonj",
},
{
  cat_id: 3,
  name: "Jute",
},
{
  cat_id: 4,
  name: "Banana Fibre",
},
{
  cat_id: 5,
  name: "Macrame",
}
]

export default function Photos() {
    const userAccessRole = localStorage.getItem('role') || 0;
    const [sku, setSku] = React.useState('');

    const [photos, setPhotos] = useState([
      {
        image: "https://i.postimg.cc/MTWQKH1g/piece5.jpg",
        name: "Product 1",
        color: "red",
        type: "bags",
        price: 500,
        qty: 50,
        description: "This is product 5",
        status: "Pending",
        cat_id: 1
      }
    ]);

    const fetchPhotos = async () => {
      const res = await axios.get(productEndpoints.PRODUCT_API, {
      }, {
        headers: {
          "ngrok-skip-browser-warning": "69420",
        }
      })
      console.log(res.data)
    }

    useEffect(() => {
     fetchPhotos()
    }, [])

    const generateSKU = async (index) => {
      const sku = (categoryMappings.find(cat => cat.cat_id === photos[index].cat_id).name.slice(0, 4) + photos[index].type +  photos[index].color.slice(0, 5)).toUpperCase()
      setSku(sku)
      toast.success("SKU generated - " + sku)
    }

    const rejectProduct = async () => {
      toast.error('Product rejected')
    }

    const handleInputChange = (index, field, value) => {
      const newPhotos = [...photos];
      newPhotos[index][field] = value;
      setPhotos(newPhotos);
    };

    return (
      <>
        <TableContainer component={Paper} >
        <Table sx={{ minWidth: 650 }} aria-label="simple table" >
          <TableHead>
            <TableRow>
              <TableCell>Image</TableCell>
              <TableCell>Name</TableCell>
              <TableCell align="center">Qty</TableCell>
              <TableCell align="center">Color</TableCell>
              <TableCell align="center">Price</TableCell>
              <TableCell align="center">Category</TableCell>
              <TableCell align="center">Description</TableCell>
              <TableCell align='right'>
                SKU
              </TableCell>
              <TableCell align="right">{userAccessRole === 0 ? "Approve" : "Verify"}</TableCell>
              <TableCell align="right">{userAccessRole === 0 ? "Reject" : "Deny"}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {
              photos.map((row, index) => (
                <TableRow
                  key={row.name}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell align="left"><img src={row.image} width={100} height={100} alt={row.name} /></TableCell>
                  <TableCell component="th" scope="row">
                    {row.name}
                  </TableCell>
                  <TableCell align="right">
                    <input type="number" value={row.qty} onChange={(e) => handleInputChange(index, 'qty', e.target.value)} width={5} />
                  </TableCell>
                  <TableCell align="right">
                    <textarea rows="1" cols="10" style={{ resize: 'none' }} value={row.color} onChange={(e) => handleInputChange(index, 'color', e.target.value)}></textarea>
                  </TableCell>
                  <TableCell align="left">
                    <textarea rows="1" cols="10" style={{ resize: 'none' }} value={row.price} onChange={(e) => handleInputChange(index, 'price', e.target.value)}></textarea>
                  </TableCell>
                  <TableCell align="right">
                    <select name="categories" onChange={
                      (e) => handleInputChange(index, 'cat_id', e.target.value)
                    }>
                    <option value="1">Teracotta</option>
                    <option value="2">Moonj</option>
                    <option value="3">Jute</option>
                    <option value="4">Banana Fibre</option>
                    <option value="5">Macrame</option>
                    </select>
                  </TableCell>
                  <TableCell align="right">
                    <textarea rows="1" cols="20" style={{ resize: 'none' }} value={row.description} onChange={(e) => handleInputChange(index, 'description', e.target.value)}></textarea>
                  </TableCell>
                  <TableCell align="right">
                    <textarea rows="1" cols="10" style={{ resize: 'none' }} value={sku} onChange={(e) => setSku(e.target.value)}></textarea>
                  </TableCell>
                  <TableCell align="right" style={{ cursor: 'pointer' }} onClick={() => generateSKU(index)}><CheckBoxIcon /></TableCell>
                  <TableCell align="right" style={{ cursor: 'pointer' }} onClick={() => rejectProduct(index)}><DeleteIcon /></TableCell>
                </TableRow>
              ))
            }
          </TableBody>
        </Table>
      </TableContainer>
      </>
    )
}