import * as React from 'react';
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
import toast from 'react-hot-toast';

const rows = [
  {
    name: "John Doe",
    phone: "1234567890",
  }
];

export default function Sellers() {
  const userAccessRole = localStorage.getItem('role') || 0;
  const [editOpen, setEditOpen] = React.useState(false);
  const [deleteOpen, setDeleteOpen] = React.useState(false);
  const [isDelete, setIsDelete] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [sellerData, setSellerData] = React.useState({
    name: "Ramu",
    phone: "9999999999",
  });
  const [addSellerOpen, setAddSellerOpen] = React.useState(false);

  const handleDeleteSeller = async () => {
    // Add delete user logic here
    setDeleteOpen(false)
    setIsDelete(true)
    setTimeout(() => {
      setIsDelete(false);
    }, 3000);
  }

  const handleEditSeller = async () => {
    // Add edit user logic here
    setEditOpen(false)
    setIsEdit(true)
    setTimeout(() => {
      setIsEdit(false);
      toast.success("Seller Edited Successfully")
    }, 3000);
  }

  const addSeller = async () => {
    // Add add user logic here
    toast.success("Seller Added Successfully")
    setAddSellerOpen(true)
  }


  return (
    <>
    <Button variant="contained" onClick={() => setAddSellerOpen(true)}>Add Sellers</Button>
        <br/>
        <br/>
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Phone No</TableCell>
            {
                userAccessRole === 0 && <>
              <TableCell align="right">Edit</TableCell>
              <TableCell align="right">Delete</TableCell>
                </>
            }
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row.name}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.name}
              </TableCell>
              <TableCell align="right">{row.phone}</TableCell>
              {
                userAccessRole === 0 && 
                <>
                   <TableCell align="right" style={{cursor: 'pointer'}} onClick={setEditOpen}><EditIcon/></TableCell>
                   <TableCell align="right" style={{cursor: 'pointer'}} onClick={setDeleteOpen}><DeleteIcon/></TableCell>
                </>
              }
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    <AlertDialog open={editOpen} setOpen={setEditOpen} title="Edit User" content="Edit user details" handleSave={handleEditSeller}>
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
        <TextField id="outlined-basic" label="Name" variant="outlined" size='small' value={sellerData.email} onChange={(e) => setSellerData({
          ...sellerData,
          name: e.target.value
        })}/>
        <TextField id="outlined-basic" label="Email" variant="outlined" size='small'
        value={sellerData.email} onChange={(e) => setSellerData({
          ...sellerData,
          email: e.target.value
        })}
        />
        <TextField id="outlined-basic" label="Phone No" variant="outlined" size='small' 
        value={sellerData.phone} onChange={(e) => setSellerData({
          ...sellerData,
          phone: e.target.value
        })}
        />
        <TextField id="outlined-basic" label="Role" variant="outlined" size='small'
        value={sellerData.role} onChange={(e) => setSellerData({
          ...sellerData,
          role: e.target.value
        })}
        />
      </Box>
    </AlertDialog>
    <AlertDialog open={editOpen} setOpen={setEditOpen} title="Add Seller" content="Edit Seller" handleSave={addSeller}>
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
        <TextField id="outlined-basic" label="Name" variant="outlined" size='small' value={sellerData.name} onChange={(e) => setSellerData({
          ...sellerData,
          name: e.target.value
        })}/>
      
        <TextField id="outlined-basic" label="Phone No" variant="outlined" size='small' value={sellerData.phone}
        onChange={(e) => setSellerData({
          ...sellerData,
          phone: e.target.value
        })}
        />
      </Box>
    </AlertDialog>
    <AlertDialog open={addSellerOpen} setOpen={setAddSellerOpen} title="Add Seller" content="Add Seller" handleSave={addSeller}>
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
        <TextField id="outlined-basic" label="Name" variant="outlined" size='small' value={sellerData.name} onChange={(e) => setSellerData({
          ...sellerData,
          name: e.target.value
        })}/>
        <TextField id="outlined-basic" label="Phone No" variant="outlined" size='small' value={sellerData.phone}
        onChange={(e) => setSellerData({
          ...sellerData,
          phone: e.target.value
        })}
        />
      </Box>
    </AlertDialog>
    <AlertDialog open={deleteOpen} setOpen={setDeleteOpen} title="Delete Seller" contentText="Do you want to delete this seller?" handleSave={handleDeleteSeller}/>
    <div style={{position: 'absolute', bottom: 0, right: 0}}>
      {
        isDelete && <Alert severity="error" onClose={() => setIsDelete(false)}>Seller deleted successfully</Alert>
      }
      {
        isEdit && <Alert severity="success" onClose={() => setIsEdit(false)}>Seller updated successfully</Alert>
      }
    </div>
    </>
  );
}
