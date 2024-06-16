import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button } from '@mui/material';
import { useDownloadExcel } from 'react-export-table-to-excel';

const rows = [
  {
    name: "John Doe",
    email: "john@doe",
    phone: "1234567890",
  }
];

export default function Buyers() {
  const tableRef = React.useRef(null)
  
  const { onDownload } = useDownloadExcel({
    currentTableRef: tableRef.current,
    filename: 'Sales',
    sheet: 'sales'
  })

  const exportToXlsx = () => {
    onDownload()
  }

  return (
    <>
    <Button variant="contained" onClick={exportToXlsx}>Export Excel</Button>
    <br/>
    <br/>
    <TableContainer component={Paper} ref={tableRef}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table" >
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Email</TableCell>
            <TableCell align="right">Phone No</TableCell>
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
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </>
  );
}
