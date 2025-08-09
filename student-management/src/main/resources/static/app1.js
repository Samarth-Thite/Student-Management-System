<script>
  function showInfo(column) {
    let message = '';
    switch (column) {
      case 0:
        message = "🆔 ID: Unique identifier for each student.";
        break;
      case 1:
        message = "📛 Name: Full name of the student.";
        break;
      case 2:
        message = "📚 Course: Course the student is enrolled in.";
        break;
      case 3:
        message = "💸 Fee: Total fee paid or to be paid by the student.";
        break;
      default:
        message = "ℹ️ Column information not found.";
    }

    // You can use alert OR better: a custom toast or modal
    alert(message);
  }
</script>
