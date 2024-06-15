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

const rows = [
  {
    name: "John Doe",
    email: "john@doe",
    phone: "1234567890"
  }
];

export default function Users() {
  const [editOpen, setEditOpen] = React.useState(false);
  const [deleteOpen, setDeleteOpen] = React.useState(false);

  return (
    <>
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
              <TableCell align="right" style={{cursor: 'pointer'}} onClick={setEditOpen}><EditIcon/></TableCell>
              <TableCell align="right" style={{cursor: 'pointer'}} onClick={setDeleteOpen}><DeleteIcon/></TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    <AlertDialog open={editOpen} setOpen={setEditOpen} title="Edit User" content="Edit user details">
      <input type="text" placeholder="Name"/>
      <input type="email" placeholder="Email"/>
      <input type="tel" placeholder="Phone No"/>
    </AlertDialog>
    <AlertDialog open={deleteOpen} setOpen={setDeleteOpen} title="Delete User" contentText="Do you want to delete this user?"/>
    </>
  );
}
