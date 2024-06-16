import React from 'react'
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
import { Alert, Box, Button, TextField } from '@mui/material';

export default function Categories() {
    const [categories, useCategories] = React.useState([{
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
  ]);

  const [addCategoryOpen, setAddCategoryOpen] = React.useState(false);

  const [newCategory, setNewCategory] = React.useState({
    name: "",
    cat_id: "",
  });
    

    const addCategories = async () => { 
      // add category
      setAddCategoryOpen(true)
    }

    return (
      <>
          <Button variant="contained" onClick={addCategories}>Add Category</Button>
        <br/>
        <br/>
        <TableContainer component={Paper} >
        <Table sx={{ minWidth: 650 }} aria-label="simple table" >
          <TableHead>
            <TableRow>
              <TableCell>Category Id</TableCell>
              <TableCell>Category Name</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {
              categories.map((row) => (
                <TableRow
                key={row.cat_id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {row.cat_id}
                  </TableCell>
                  <TableCell>{row.name}</TableCell>
                </TableRow>
              ))
              }
          </TableBody>
        </Table>
      </TableContainer>
      <AlertDialog open={addCategoryOpen} setOpen={setAddCategoryOpen} title="Add Category" content="Add category" handleSave={addCategories}>
    <Box
      component="form"
      sx={{
        display: 'flex',
        flexDirection: 'column',
        '& > :not(style)': { m: 1, width: '25ch' },
      }}
      noValidate
      autoComplete="off"
    >
      <TextField id="outlined-basic" label="Category Id" variant="outlined" />
      <TextField id="outlined-basic" label="Category Name" variant="outlined" />
      </Box>
    </AlertDialog>
      </>
    )
}