$(document).idle({
    onIdle: function () {
        window.location.reload();
    },
    onActive: function () {
      window.location.reload();
    },
    idle: 60000
});

