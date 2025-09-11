(function(){
      angular.module('mediaGalleryApp', [])
        .controller('GalleryCtrl', ['$scope', '$sce', function($scope, $sce){
          var vm = this;

          // simple user object
          vm.user = { name: 'Gabriel Cardoso', email: 'gabriel@example.com', bio: 'Loves photos and short clips' };

          vm.showProfile = false;
          vm.toggleProfile = function(){ vm.showProfile = !vm.showProfile; };
          vm.editProfile = function(){ alert('Open a profile editor (not implemented in this demo)'); };
          vm.logout = function(){ alert('Logout (demo)'); };

          // media list, small demo items (images/videos by URL)
          vm.media = [
            { id:1, type:'image', src:'https://picsum.photos/seed/1/800/600', title:'Random 1', tags:['random','demo'], favorite:false },
            { id:2, type:'image', src:'https://picsum.photos/seed/2/800/600', title:'Random 2', tags:['photo'], favorite:false },
            { id:3, type:'video', src:'https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4', title:'Sample Video', tags:['sample','video'], favorite:false }
          ];

          vm.newMedia = { url:'', title:'', tags:'' };

          // utility: guess type by extension
          function guessType(url){
            if(!url) return 'unknown';
            var u = url.split('?')[0].toLowerCase();
            if(u.match(/\.(jpg|jpeg|png|gif|webp|bmp)$/)) return 'image';
            if(u.match(/\.(mp4|webm|ogg|mov|mkv)$/)) return 'video';
            // fallback: treat images if it contains "image"
            if(u.indexOf('image') !== -1) return 'image';
            return 'unknown';
          }

          // Add by URL
          vm.addByUrl = function(){
            if(!vm.newMedia.url) return alert('Paste a URL first');
            var type = guessType(vm.newMedia.url);
            var tags = vm.newMedia.tags ? vm.newMedia.tags.split(',').map(function(t){return t.trim();}) : [];
            var item = { id: Date.now(), type: type, src: vm.newMedia.url, title: vm.newMedia.title || '', tags: tags, favorite:false };
            vm.media.unshift(item);
            vm.newMedia = { url:'', title:'', tags:'' };
            $scope.$applyAsync();
          };

          // handle local file upload (reads as data URL)
          vm.handleFile = function(files){
            if(!files || !files.length) return;
            var f = files[0];
            var reader = new FileReader();
            reader.onload = function(e){
              var src = e.target.result;
              var type = f.type.indexOf('video') === 0 ? 'video' : (f.type.indexOf('image') === 0 ? 'image' : 'unknown');
              vm.media.unshift({ id: Date.now(), type: type, src: src, title: f.name, tags:[], favorite:false });
              $scope.$apply();
            };
            reader.readAsDataURL(f);
            // clear the input so same file can be reselected
            document.getElementById('fileInput').value = '';
          };

          // filter for search + type
          vm.query = '';
          vm.filterType = 'all';
          vm.searchFilter = function(item){
            // type filter
            if(vm.filterType !== 'all' && item.type !== vm.filterType) return false;

            // text filter (on title, tags)
            if(!vm.query) return true;
            var q = vm.query.toLowerCase();
            if((item.title || '').toLowerCase().indexOf(q) !== -1) return true;
            if((item.tags || []).join(' ').toLowerCase().indexOf(q) !== -1) return true;
            return false;
          };

          vm.toggleFavorite = function(m){ m.favorite = !m.favorite; };

          // modal
          vm.modalOpen = false; vm.modalItem = null;
          vm.openModal = function(m){ vm.modalItem = m; vm.modalOpen = true; };
          vm.closeModal = function(){ vm.modalOpen = false; vm.modalItem = null; };
          $scope.modalIsImage = function(){ return vm.modalItem && vm.modalItem.type === 'image'; };
          $scope.modalIsVideo = function(){ return vm.modalItem && vm.modalItem.type === 'video'; };

        }]);

      // small helper so file input onchange can call controller method via scope().vm
      // The onchange attribute in the file input uses: angular.element(this).scope().vm.handleFile(this.files)
    })();