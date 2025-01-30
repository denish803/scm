  
function exportContactData(tableID, filename = "data") {
  const table = document.getElementById(tableID);
  const workbook = XLSX.utils.table_to_book(table, { sheet: "Sheet1" });
  XLSX.writeFile(workbook, `${filename || 'export'}.xlsx`);

}
