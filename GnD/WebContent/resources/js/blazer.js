$(document).ready(function() {
    $('.faux-select').each(function() {
        var $display = $(this);
        $(this).siblings('select').on('change', function() {
            $display.text(this.value);
        });
    });

    $('.slider.steps [data-stepcount]').each(function() {
        var stepcount = +$(this).data('stepcount');
        for (var i = 0; i <= stepcount; i++) {
            $step = $('<div class="slider-step">');
            $step.css({left: (100 / stepcount) * i + '%'});
            $(this).append($step);
        }
    });

    $('.slider-handle').on('mousedown', function(e) {
        var $handle = $(this);
        var handleWidth = $handle.outerWidth();
        var $track = $(this).siblings('.slider-track');
        var trackLeft = $track.offset().left;
        var trackRight = $track.width() + trackLeft;
        var $steps = $handle.siblings('.slider-steps');
        var stepCount = $steps.data('stepcount');
        $handle.addClass('dragging');
        $(document).on('mousemove.slider-drag', function(e) {
            var x = e.clientX;
            x = Math.max(x, trackLeft);
            x = Math.min(x, trackRight);
            x -= handleWidth / 2;
            $handle.offset({
                left: x
            });
        }).on('mouseup.slider-drag', function(e) {
            $handle.removeClass('dragging');
            $(this).off('.slider-drag');
        });
    });

    $('.list li').mousedown(function(e) {
        var $parentSelect = $(this).closest('.select-menu');
        if ($parentSelect) {
            $parentSelect.find('input').val($(this).text());
        }
    });

    $('.checklist-tree li.folder').click(function(e) {
        e.stopPropagation();
        var isClosed = $(this).hasClass('closed');
        $(this)
            .toggleClass('closed', !isClosed)
            .toggleClass('open', isClosed);
    });

    $('.checklist-tree li').click(function(e) {
        e.stopPropagation();
    });

    $('.navigation li.folder').click(function(e) {
        e.stopPropagation();
        var isClosed = $(this).hasClass('closed');
        $(this).siblings('.folder').addClass('closed').removeClass('open');
        $(this)
            .toggleClass('closed', !isClosed)
            .toggleClass('open', isClosed);
    });

    $('.navigation li.folder li, .navigation li:not(.folder), .list li, .dropdown-menu li').click(function(e) {
        e.stopPropagation();
        // $(this).siblings().removeClass('selected');
        // $(this).addClass('selected');
        $('.selected').removeClass('selected');
        $(this).addClass('selected')
    });

    $('.calendar .addon, .select-menu .addon').click(function(e) {
        var $calendar = $(this).closest('.calendar, .select-menu:not(.select-menu-native)');
        var isOpen = $calendar.hasClass('open');
        $calendar
            .toggleClass('open', !isOpen)
            .toggleClass('closed', isOpen);
    });

    $('.text-field-clear:not([disabled]) + .text-field-icon').click(function(e) {
        var $input = $(this).prev('.text-field-clear');
        $input.val('');
    });

    $('.accordion .section-header').on('click', function() {
        var $section = $(this).closest('.accordion-section');
        $section.siblings().addClass('closed').removeClass('open');
        $section.toggleClass('open closed');
    });

    $('.menu-trigger').on('click', function() {
        var $dropdown = $(this).find('.dropdown-menu').first();
        $dropdown.find('.selected').removeClass('selected');
        if ($dropdown.hasClass('closed')) {
            $dropdown.toggleClass('open closed');
            setTimeout(function() {
                $(document).one('click', function(e) {
                    if ($(this).closest('.dropdown-menu').length === 0) {
                        $dropdown.removeClass('open').addClass('closed');
                    }
                });
            }, 1)
        }

        var $bubbleMenu = $(this).find('.bubble-menu');
        if ($bubbleMenu.hasClass('closed')) {
            $bubbleMenu.toggleClass('open closed');
            setTimeout(function() {
                $(document).one('click', function() {
                    if ($(this).closest('.bubble-menu').length === 0) {
                        $bubbleMenu.removeClass('open').addClass('closed');
                    }
                });
            }, 1);
        }
    });

    $('.bubble-menu').on('click', function(e) {
        e.stopPropagation();
    });

    $('.scroll-overflow-left, .scroll-overflow-right').on('click', function() {
        var $list = $(this).siblings('ul');
        var width = $list.innerWidth();
        var listLeft = $list.offset().left;
        var listRight = listLeft + width;
        var dimensions = [];
        $list.children().each(function() {
            var offset = $(this).offset();
            dimensions.push({
                $el: $(this),
                left: offset.left,
                right: offset.left + $(this).width()
            });
        });
        var movingLeft = $(this).hasClass('scroll-overflow-left');
        var movingRight = $(this).hasClass('scroll-overflow-right');
        if (movingLeft) {
            // reverse loop
            for (var each = dimensions.length - 1; each >= 0; each--) {
                var dimension = dimensions[each];
                if (dimension.left < listLeft - 30) {
                    $list.animate({'scrollLeft': $list.scrollLeft() + (dimension.left - listLeft) - 23});
                    break;
                }
            }
        }
        if (movingRight) {
            for (var each = 0; each < dimensions.length; each++) {
                var dimension = dimensions[each];
                if (dimension.right > listRight + 30) {
                    $list.animate({'scrollLeft': $list.scrollLeft() + (dimension.right - listRight) + 30});
                    break;
                }
            }
        }
    });

    // Animation fallback for IE 9.
    // Requires modernizr.js
    animateMovingStripes(
        $('html.no-csstransitions')
            .find('.progress-striped, .progress-indeterminate')
            .find('.progress-indicator')
    );

    function animateMovingStripes($el) {
        $el.css('background-position', '0');
        $el.animate(
            {'background-position': '22'}, 1000, 'linear', function() {
            animateMovingStripes($el);
        });
    };
});


