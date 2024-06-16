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
    email: "john@doe",
    phone: "1234567890",
  }
];

export default function Users() {
  const [editOpen, setEditOpen] = React.useState(false);
  const [deleteOpen, setDeleteOpen] = React.useState(false);
  const [userData, setUserData] = React.useState({
    name: "Harish",
    email: "harish@gmail.com",
    phone: "9999999999",
  });
  const [isDelete, setIsDelete] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [addUserOpen, setAddUserOpen] = React.useState(false);

  const handleDeleteUser = async () => {
    // Add delete user logic here
    setDeleteOpen(false)
    setIsDelete(true)
    setTimeout(() => {
      setIsDelete(false);
    }, 3000);
  }

  const handleEditUser = async () => {
    // Add edit user logic here
    setEditOpen(false)
    setIsEdit(true)
    setTimeout(() => {
      setIsEdit(false);
    }, 3000);
  }

  const addUser = async () => {
    // Add add user logic here
    toast.success("Sub-Admin Added Successfully")
    setAddUserOpen(true)
  }

  return (
    <>
     <Button variant="contained" onClick={addUser}>Add Sub-Admin</Button>
        <br/>
        <br/>
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Email</TableCell>
            <TableCell align="right">Phone No</TableCell>
            <TableCell align="right">Edit</TableCell>
            <TableCell align="right">Delete</TableCell>
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
              <TableCell align="right">{row.email}</TableCell>
              <TableCell align="right">{row.phone}</TableCell>
              <TableCell align="right" style={{cursor: 'pointer'}} onClick={() => { setUserData(row); setEditOpen(true)}}><EditIcon/></TableCell>
              <TableCell align="right" style={{cursor: 'pointer'}} onClick={() => { setUserData(row); setDeleteOpen(true)}}><DeleteIcon/></TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    <AlertDialog open={editOpen} setOpen={setEditOpen} title="Edit Sub-Admin" content="Edit sub-admin" handleSave={handleEditUser}>
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
        <TextField id="outlined-basic" label="Name" variant="outlined" size='small' value={userData.name} onChange={(e) => setUserData({
          ...userData,
          name: e.target.value
        })}/>
        <TextField id="outlined-basic" label="Email" variant="outlined" size='small' value={userData.email} onChange={(e) => setUserData({
          ...userData,
          email: e.target.value
        
        })}/>
        <TextField id="outlined-basic" label="Phone No" variant="outlined" size='small' value={userData.phone}
        onChange={(e) => setUserData({
          ...userData,
          phone: e.target.value
        })}
        />
      </Box>
    </AlertDialog>
    <AlertDialog open={addUserOpen} setOpen={setAddUserOpen} title="Add Sub-Admin" content="Add Sub-Admin" handleSave={addUser}>
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
        <TextField id="outlined-basic" label="Name" variant="outlined" size='small' value={userData.name} onChange={(e) => setUserData({
          ...userData,
          name: e.target.value
        })}/>
        <TextField id="outlined-basic" label="Email" variant="outlined" size='small' value={userData.email} onChange={(e) => setUserData({
          ...userData,
          email: e.target.value
        
        })}/>
        <TextField id="outlined-basic" label="Phone No" variant="outlined" size='small' value={userData.phone}
        onChange={(e) => setUserData({
          ...userData,
          phone: e.target.value
        })}
        />
      </Box>
    </AlertDialog>
    <AlertDialog open={deleteOpen} setOpen={setDeleteOpen} title="Delete Sub-Admin" contentText="Do you want to delete this sub-admin?" handleSave={handleDeleteUser}/>
    <div style={{position: 'absolute', bottom: 0, right: 0}}>
    {
      isDelete && <Alert severity="error" onClose={() => setIsDelete(false)}>Sub-Admin deleted successfully</Alert>
    }
    {
      isEdit && <Alert severity="success" onClose={() => setIsEdit(false)}>Sub-Admin updated successfully</Alert>
    }
    </div>
    </>
  );
}
