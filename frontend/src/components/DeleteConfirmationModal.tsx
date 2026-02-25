import { Modal, Button } from "react-bootstrap";

const DeleteConfirmationModal = ({ show, handleClose, handleConfirm, message }) => {
  return (
    <Modal className="modal-sm" backdrop="static" show={show} onHide={handleClose} centered>
      <Modal.Header
        className="p-0 m-0 border-0 d-flex position-absolute top-0 end-0 z-1"
        closeButton
      ></Modal.Header>
      <div className="modal-status bg-danger"></div>
      <Modal.Body className="text-center py-4">
        <i className="ti ti-alert-triangle mb-2 text-danger" style={{ fontSize: 56 }}></i>
        <h3>Are you sure?</h3>
        <div className="text-secondary">{message}</div>
      </Modal.Body>
      <Modal.Footer>
        <div className="w-100">
          <div className="row">
            <div className="col">
              <Button className="w-100" variant="" onClick={handleClose}>
                Cancel
              </Button>
            </div>
            <div className="col">
              <Button className="w-100" variant="danger" onClick={handleConfirm}>
                Delete
              </Button>
            </div>
          </div>
        </div>
      </Modal.Footer>
    </Modal>
  );
};

export default DeleteConfirmationModal;
