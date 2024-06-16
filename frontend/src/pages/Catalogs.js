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
import { Alert, Box, TextField } from '@mui/material';

export default function Catalogs() {
    const [catalogs, useCatalogs] = React.useState([{
        SKU: "123",
        name: "Product 1",
        Price: 100,
        Qty: 10,
        Description: "This is product 1",
        image: "https://i.postimg.cc/d3fkB9gK/piece1.png'",
        cid: ""
    },
    {
        SKU: "124",
        name: "Product 2",
        Price: 200,
        Qty: 20,
        Description: "This is product 2",
        image: "https://i.postimg.cc/mD8Fc4wk/piece2.png",
        cid: ""
    },
    {
        SKU: "125",
        name: "Product 3",
        Price: 300,
        Qty: 30,
        Description: "This is product 3",
        image: "https://i.postimg.cc/4yyHV0L5/piece3.jpg",
        cid: ""
    },
    {
        SKU: "126",
        name: "Product 4",
        Price: 400,
        Qty: 40,
        Description: "This is product 4",
        image: "https://i.postimg.cc/KY7T2B3n/piece4.jpg",
        cid: ""
    },
    {
        SKU: "127",
        name: "Product 5",
        Price: 500,
        Qty: 50,
        Description: "This is product 5",
        image: "https://i.postimg.cc/MTWQKH1g/piece5.jpg",
        cid: ""
    }
  ])

    // add api for catlogs

    const [catalog, setCatalog] = React.useState({
        SKU: "",
        name: "",
        Price: 100,
        Qty: 10,
        Description: "This is product 1",
        image: "",
        cid: ""
    })

    const [editOpen, setEditOpen] = React.useState(false);
    const [deleteOpen, setDeleteOpen] = React.useState(false);
    const [isDelete, setIsDelete] = React.useState(false);
    const [isEdit, setIsEdit] = React.useState(false);
    
    const handleDeleteCatalog = async () => {
      // Add delete user logic here
      setDeleteOpen(false)
      setIsDelete(true)
      setTimeout(() => {
        setIsDelete(false);
      }, 3000);
    }
  
    const handleEditCatalog = async () => {
      // Add edit user logic here
      setEditOpen(false)
      setIsEdit(true)
      setTimeout(() => {
        setIsEdit(false);
      }, 3000);
    }

    return (
      <>
        <TableContainer component={Paper} >
        <Table sx={{ minWidth: 650 }} aria-label="simple table" >
          <TableHead>
            <TableRow>
              <TableCell>SKU</TableCell>
              <TableCell>Image</TableCell>
              <TableCell>Name</TableCell>
              <TableCell align="right">Qty</TableCell>
              <TableCell align="right">Description</TableCell>
              <TableCell align="right">Price</TableCell>
              <TableCell align="right">Edit</TableCell>
              <TableCell align="right">Delete</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {
              catalogs.map((row) => (
                <TableRow
                key={row.name}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell align="left">{row.SKU}</TableCell>
                  <TableCell align="left"><img src={row.image} width={200} height={180}/></TableCell>
                  <TableCell component="th" scope="row">
                    {row.name}
                  </TableCell>
                  <TableCell align="right">{row.Qty}</TableCell>
                  <TableCell align="right">{row.Description}</TableCell>
                  <TableCell align="right">{row.Price}</TableCell>
                  <TableCell align="right" style={{cursor: 'pointer'}} onClick={setEditOpen}><EditIcon/></TableCell>
                  <TableCell align="right" style={{cursor: 'pointer'}} onClick={setDeleteOpen}><DeleteIcon/></TableCell>
                </TableRow>
              ))
              }
          </TableBody>
        </Table>
      </TableContainer>
      <AlertDialog open={editOpen} setOpen={setEditOpen} title="Edit Catalog" content="Edit catalog details" handleSave={handleEditCatalog}>
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
        <TextField id="outlined-basic" label="Name" variant="outlined" size='small' value={catalog.name} onChange={(e) => setCatalog({
          ...catalog,
          name: e.target.value
        })}/>
        <TextField id="outlined-basic" label="Qty" variant="outlined" size='small' 
        value={catalog.Qty} onChange={(e) => setCatalog({
          ...catalog,
          Qty: e.target.value
        })}
        />
        <TextField id="outlined-basic" label="Description" variant="outlined" size='small'
        value={catalog.Description} onChange={(e) => setCatalog({
          ...catalog,
          Description: e.target.value
        })}
        />
        <TextField id="outlined-basic" label="Price" variant="outlined" size='small'
        value={catalog.Price} onChange={(e) => setCatalog({
          ...catalog,
          Price: e.target.value
        })}
        />

      </Box>
    </AlertDialog>
    <AlertDialog open={deleteOpen} setOpen={setDeleteOpen} title="Delete Catalog" contentText="Do you want to delete this catalog?" handleSave={handleDeleteCatalog}/>
    <div style={{position: 'absolute', bottom: 0, right: 0}}>
      {
        isDelete && <Alert severity="error" onClose={() => setIsDelete(false)}>Catalog deleted successfully</Alert>
      }
      {
        isEdit && <Alert severity="success" onClose={() => setIsEdit(false)}>Catalog updated successfully</Alert>
      }
    </div>
      </>
    )
}