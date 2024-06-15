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

const Requests = () => {
  
  const [editOpen, setEditOpen] = React.useState(false);
  const [deleteOpen, setDeleteOpen] = React.useState(false);
  const [isDelete, setIsDelete] = React.useState(false);
  const [isEdit, setIsEdit] = React.useState(false);
  const [requests, setRequests] = React.useState([
    {
      rid: 1,
      RStatus: "Pending",
      RQty: 20,
      RDesc: "This is a request for product 1",
    },
    {
      rid: 2,
      RStatus: "Pending",
      RQty: 30,
      RDesc: "This is a request for product 2",
    },
    {
      rid: 3,
      RStatus: "Pending",
      RQty: 40,
      RDesc: "This is a request for product 3",
    },
    {
      rid: 4,
      RStatus: "Pending",
      RQty: 50,
      RDesc: "This is a request for product 4",
    },
    {
      rid: 5,
      RStatus: "Pending",
      RQty: 60,
      RDesc: "This is a request for product 5",
    }
  ]);
  
  const handleApprove = async () => {
    // handle approval of request
  }

  const handleReject = async () => {
    // handle rejection of request
  }

  return (
    <>
      <TableContainer component={Paper} >
      <Table sx={{ minWidth: 650 }} aria-label="simple table" >
        <TableHead>
          <TableRow>
            <TableCell>Rid</TableCell>
            <TableCell>Status</TableCell>
            <TableCell align="right">Qty</TableCell>
            <TableCell/>
            <TableCell/>
          </TableRow>
        </TableHead>
        <TableBody>
          {
            requests.map((row) => (
              <TableRow
              key={row.rid}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                <TableCell align="left">{row.rid}</TableCell>
                <TableCell align="left">{row.RStatus}</TableCell>
                <TableCell align="right">{row.RQty}</TableCell>
                <TableCell align="right">
              <Button color="primary" variant='contained'>
                Approve
              </Button>
            </TableCell>
            <TableCell align="right">
              <Button color="error" variant='contained'>
                Reject
              </Button>
            </TableCell>
              </TableRow>
            ))
            }
        </TableBody>
      </Table>
    </TableContainer>
    </>
  )
}

export default Requests